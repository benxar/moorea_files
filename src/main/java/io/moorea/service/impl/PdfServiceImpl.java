package io.moorea.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import io.moorea.entity.Attachment;
import io.moorea.entity.CertificateSubject;
import io.moorea.entity.Signer;
import io.moorea.model.JsonResult;
import io.moorea.parser.request.AttachToPdfRequest;
import io.moorea.service.PdfService;

@Service
public class PdfServiceImpl implements PdfService {

	@Override
	public JsonResult htmlToPdf(InputStream html) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, html);
			document.close();
			// tmp
			// FileOutputStream fos = new FileOutputStream("/tmp/test.pdf");
			// fos.write(baos.toByteArray());
			// fos.close();
			return new JsonResult(true, "Success", Base64.encodeBytes(baos.toByteArray()));
		} catch (DocumentException e) {
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		}
	}

	@Override
	public JsonResult validatePdfFormat(String b64Pdf) {
		boolean encripted = false;
		try {
			PdfReader pdfReader = new PdfReader(Base64.decode(b64Pdf));
			AcroFields af = pdfReader.getAcroFields();
			ArrayList<String> names = af.getSignatureNames();
			if (names.size() > 0)
				encripted = true;
			return new JsonResult(true, "Success", encripted);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "El archivo enviado no se corresponde con el formato tipo PDF");
		}
	}

	@Override
	public JsonResult addDocument(AttachToPdfRequest req, boolean encrypted) {
		try {
			PdfReader reader = new PdfReader(Base64.decode(req.getB64()));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper stamper = null;
			if (encrypted) {
				Document nDoc = new Document();
				PdfCopy copier = new PdfCopy(nDoc, baos);
				nDoc.open();
				copier.addDocument(reader);
				nDoc.close();
				PdfReader nReader = new PdfReader(baos.toByteArray());
				stamper = new PdfStamper(nReader, baos);
				stamper.setFormFlattening(true);
				String attchName = "original_signed_document";
				PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(stamper.getWriter(), null,
						attchName + ".pdf", Base64.decode(req.getB64()));
				stamper.addFileAttachment(attchName + ".pdf", fs);
			} else {
				stamper = new PdfStamper(reader, baos);
			}
			for (Attachment attachment : req.getlAttach()) {
				PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(stamper.getWriter(), null,
						attachment.getName() + "." + attachment.getExtension(), Base64.decode(attachment.getB64()));
				stamper.addFileAttachment(attachment.getName() + "." + attachment.getExtension(), fs);
			}
			stamper.close();
			// tmp
			FileOutputStream fos = new FileOutputStream("/tmp/test.pdf");
			fos.write(baos.toByteArray());
			fos.close();
			return new JsonResult(true, "Success", Base64.encodeBytes(baos.toByteArray()));
		} catch (DocumentException e) {
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		}
	}

	@Override
	public List<Signer> getSigners(String b64Pdf) {
		PdfReader reader = null;
		List<Signer> signers = null;
		try {
			signers = new ArrayList<Signer>();
			reader = new PdfReader(Base64.decode(b64Pdf));
			AcroFields af = reader.getAcroFields();
			ArrayList<String> names = af.getSignatureNames();
			for (String name : names) {
				// System.out.println("Signature name: " + name);
				// System.out.println("Signature covers whole document: " +
				// af.signatureCoversWholeDocument(name));
				// System.out.println("Document revision: " +
				// af.getRevision(name) + " of " + af.getTotalRevisions());
				Security.addProvider(new BouncyCastleProvider());
				// InputStream ip = af.extractRevision(name);
				PdfPKCS7 pk = af.verifySignature(name);
				// Calendar cal = pk.getSignDate();
				// System.out.println(new
				// Date(pk.getSignDate().getTimeInMillis()));
				// Certificate[] pkc = pk.getCertificates();
				// System.out.println("Subject: " +
				// CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
				// System.out.println("Revision modified: " + !pk.verify());
				CertificateSubject cs = new CertificateSubject(pk.getSigningCertificate());
				signers.add(new Signer(name, cs));
			}
		} catch (Exception e) {
			e.printStackTrace();
			signers = null;
		}
		return signers;
	}

	@Override
	public UUID getKey(String b64Pdf) {
		PdfReader reader = null;
		UUID result = null;
		try {
			reader = new PdfReader(Base64.decode(b64Pdf));
			Map<String, String> auxInfo = reader.getInfo();
			if (auxInfo != null) {
				result = UUID.fromString(auxInfo.get("internalkey"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

}
