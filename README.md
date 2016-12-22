## TODO
- [x] agregar el campo sigla a la entidad document, el cual se va a estampar en cada documento anexado al hacer next_number 
- [x] agregar endpoint para buscar expediente segun categoria, oficina y año
- [x] validar que el expediente no exista con la misma oficina, categoria y año, devolver error, ya existe
- [x] al ponerle la estampa agregar que si el pdf esta firmado, que cree una copia nueva de la version anterior y lo adjunte al mismo
- [x] al adjuntar agregar que si el pdf esta firmado, que cree una copia nueva de la version anterior y lo adjunte al mismo
- [x] validar que realmente sea un pdf, en caso contrario devolver que es formato invalido
- [x] agregar a la estampa el param_text usando este formato: param_text + " 000" + number + "/" + year
- [x] now the key is saved directly in the pdf. We must check if this key is correct before saving it
- [ ] agregar la posibilidad de subir archivos no asociados a ningun expediente y luego asociarlos
- [ ] agregar la posibilidad de subir archivos en formato multipart en vez de base64
- [ ] hacer que se modifique la ultima fecha de actualizacion del documento y la cantidad de actualizaciones
- [ ] return http errors when the format of the messages is incorrect (now only returning success=false)

## API

```javascript
[
	{
		"method": "POST",
		"path": "/api/files/manager",
		"request": {
			"type": "book",
			"header": {
				"office": {"id": "H00000001", "text": "Dirección General"},
				"category": {"id": "resolucion", "text": "Resolución"},
				"year": 2017,
				"prefix": "JWS"
			}
		},
		"response": {
			"success": true,
			"result": {
				"id": "wefafkhsejhfgefh34@#$@#4"
			}
		},
		"description": "Create a new file (could be a file or a book)",
		"logic": "Creates a new file on the files mongo collection"
	},
	{//nota: agregado recientemente por pedido de juan
		"method": "GET",
		"path": "/api/files/manager/<officeId>/<categoryId>/<year>",//officeId=string;categoryId=string;year=int
		"response": {
			"success": true,
			"result":
				{
					"id": "wefafkhsejhfgefh34@#$@#4"
				}
		},
		"description": "Finds a single document given the officeid, the categoryid and the year",
		"logic": "Only returns the id"
	},
	{
		"method": "GET",
		"path": "/api/files/manager/<type>/<page>/<limit>/<order_filed>/<order_direction>",//type=string;page=int;limit=int;order_filed=string;order_direction=string
		"response": {
			"success": true,
			"result": [
				{
					"id": "wefafkhsejhfgefh34@#$@#4",
					"type": "book",
					"updates": 23,
					"year": 2016,
					"office": {"id": "H00000001", "text": "Dirección General"},
					"last_update": "25/12/2016",
					"category": {"id": "resolucion", "text": "Resolucion"}
				}
			]
		},
		"description": "Get list of files given a type, a page number, a limit and an order",
		"logic": "for each doc it needs to look for data of the last doc inserted"
	},
	{
		"method": "GET",
		"path": "/api/files/manager/<id>",
		"response": {
			"success": true,
			"result": {
				"file": {
					"id": "file_1",
					"type": "book",
					"updates": 23,
					"year": 2016,
					"office": {"id": "H00000001", "text": "Dirección General"},
					"last_update": "25/12/2016",
					"category": {"id": "resolucion", "text": "Resolucion"}
				},
				"index ": [ //agregar archivos adjuntos de forma separada en el indice
					{
						"file_id": "file_1",
						"doc_id": "doc_1"
						"parent": "root",
						"doc": {
							"path": "",
							"name": ""
						}
					},
					{
						"file_id": "file_1",
						"doc_id": "doc_2"
						"parent": "doc_1",
						"doc": {
							"path": "",
							"name": ""
						}
					}
				]
			}
		},
		"description": "Get index for file",
		"logic": "Get data from the file collection and data from the indexes collection"
	},
	{
		"method": "GET",
		"path": "/api/files/manager/<id>/documents/<number>",
		"response": {
			"success": true,
			"result": {
				"doc": {
					"b64": "",
					"name": ""
				},
				"signers":[
					{
						"name": "Persona1",
						"ca": ""
					},
					{
						"name": "Persona2",
						"ca": ""
					}
				],
				"atachments":[{
					"id": "eefrsf",
					"name": "nota2.txt",
					"path": "/"
				},{
					"id": "tdhdref",
					"name": "nota3.xls",
					"path": "/"
				}]
			}
		},
		"description": "Get b64 file",
		"logic": "Get the file path from the indexes collection using the id and number keys and return it"
	},
	{
		//nota: cuando piden numero siguiente, el expediente se bloquea y no es posible insertar nuevos documentos y 
		//	esta funcion devolvera error mientras este bloqueado
		"method": "POST",
		"path": "/api/files/next_number/<id>",
		"request": {
			"b64": "aefsefesf",
			//"param_text": "file_1"
		},
		"response": {
			"success": true,
			"result": {
				"b64": "bsargaekbhAEFJHaefi",
				"number": 4,
				"key": "e1d23f53-006c-4731-8357-e506703523a9" //uuid random generado para validacion en posterior insert
			}
		},
		"description": "Book next number in redis and insert it in the pdf and then return its b64",
		"logic": "Lock the number booking in redis and return the number inside the b64"
	},
	{
		"method": "PUT",//nota: antes era POST, se cambio a PUT porque colisionaba con otro endpoint por modificaciones
		"path": "/api/files/manager",//nota: antes la ruta seguia con "/<id>/<number>" pero se saco, agregando el hash generado en el archivo pdf a subir. Por eso se cambio el metodo a PUT 
		"request": {
			"b64": "aefsefesf",
			"name": ""
		},
		"response": {
			"success": true,
			"result": {
				"file_id": "file_1",
				"doc_id": "doc_2",
				"parent": "doc_1",
				"payload": {},
				"doc": {
					"path": "",
					"name": ""
				}
			}
		},
		"description": "Add doc to file",
		"logic": "Checks if its the corresponding number and add it to the index and to the docs collection, it then deletes the redis key"
	},
	{
		"method": "POST",
		"path": "/api/files/helpers/atach_doc_to_pdf",
		"request": {
			"b64": "aefsefesf",
			"atachments":[{
				"id": "eefrsf",
				"name": "nota2",
				"extension": "txt",
				"b64": "dsadi$AFDSDSadsadas" // nota: antes era con rutas desde el fs -> "path": "/"
			},{
				"id": "tdhdref",
				"name": "nota3",
				"extension": "xls",
				"b64": "dsadi$AFDSDSadsadas" // nota: antes era con rutas desde el fs -> "path": "/"
			}]
		},
		"response": {
			"success": true,
			"result": {
				"doc": {
					"path": "",
					"name": "",
					"hash": ""
				}
			}
		},
		"description": "Atach doc to a pdf",
		"logic": "Atach, and return hash"
	},
	{
		"method": "POST",
		"path": "/api/files/helpers/convert_to_pdf",
		"request": {
			"origin": "html", //.xls, .doc, .html, .xlsx, .docx
			"html": "<html></html>",
			//"path": ""
		},
		"response": {
			"success": true,
			"result": {
				"doc": {
					"path": "",
					"name": "",
					"hash": ""
				}
			}
		},
		"description": "Convert html to pdf",
		"logic": "Convert, save it, and return hash and path"
	},
	{
		"method": "POST",
		"path": "/api/files/helpers/validations/type_of_file",
		"request": {
			"b64": "ewfsef"
		},
		"response": {
			"success": true,
			"result": {
				"pdf": true,
				"protected": false
			}
		},
		"description": "return if its a pdf and if its protected",
		"logic": ""
	},
	{
		"method": "POST",
		"path": "/api/files/helpers/validations/signers",
		"request": {
			"b64": "ewfsef"
		},
		"response": {
			"success": true,
			"result": {
				"signers":[
					{
						"name": "Persona1",
						"ca": ""
					},
					{
						"name": "Persona2",
						"ca": ""
					}
				]
			}
		},
		"description": "return signers of b64 pdf",
		"logic": ""
	}

]
```