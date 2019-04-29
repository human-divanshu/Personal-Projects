import requests
import json

dateStr = str(raw_input("Enter a date for domain reg in yyyy-mm-dd format : "))
fileName = str(raw_input("Enter a file name to process : "))

def file_to_list(fileName):
	data = []
	fp = open(fileName, "r")
	text = fp.read()
	fp.close()
	for w in text.split("\n"):
		if len(w.strip()) > 0:
			data.append(w.strip())
	return data	

domains = file_to_list(fileName)

url = "http://localhost:8080/domain/add"
querystring = {"date": dateStr, "domainList" : domains}
payload = json.dumps(querystring)

headers = {
    'Content-Type': "application/json",
    'Accept': "application/json",
    'Cache-Control': "no-cache",
    'Postman-Token': "1abfd0e4-a76c-4a8b-a4d5-9cea1a952001"
    }

response = requests.request("PUT", url, headers=headers, data=str(payload))

print(response.text)