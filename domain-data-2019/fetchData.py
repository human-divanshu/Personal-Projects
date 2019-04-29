import requests
import json
import time

dateStr = str(raw_input("Enter date into yyyy-mm-dd format: "))

# com, info, org, net
zoneList = ["com", "info", "org", "net"]
page = 1
totalPage = 1 # will be updated below
url = "https://dnpedia.com/tlds/ajax.php"
headers = {
    'User-Agent': "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:65.0) Gecko/20100101 Firefox/65.0",
    'Accept': "application/json, text/javascript, */*; q=0.01",
    'Accept-Language': "en-US,en;q=0.5",
    'Referer': "https://dnpedia.com/tlds/ndaily.php",
    'X-Requested-With': "XMLHttpRequest",
    'DNT': "1",
    'Connection': "keep-alive",
    'Cache-Control': "no-cache",
    'Postman-Token': "8fdfad11-7691-4bfe-961d-8841ce4c3b18"
    }

jsonData = None

def is_ascii(s):
    return all(ord(c) < 128 for c in s)


for zone in zoneList:
    page = 1
    fileName = "data/" + dateStr + "-" + zone + ".txt"
    while page <= totalPage:
        try:
            querystring = {"cmd":"added","columns":"id,name,length,idn,thedate,","ecf":"zoneid,thedate","ecv":"1,"+dateStr,"zone":zone,"_search":"false","rows":"2000","page":page,"sidx":"length","sord":"asc"}
            print("Fetching page number " + str(page) + " of total pages " +  str(totalPage)+ " for zone " + str(zone))
            response = requests.request("GET", url, headers=headers, params=querystring)
            jsonData = json.loads(response.text)
            newPage = jsonData['page']
            total = jsonData['total']
            domainData = jsonData['rows']
            domains = set()
            for d in domainData:
                try:
                    if is_ascii(d['name']):
                        domains.add(d['name'].strip())
                except Exception, e:
                    print("Error in reading domain name")
            page = int(newPage) + 1
            totalPage = int(total)

            # write to file
            fp = open(fileName, "a")
            for d in domains:
                fp.write(d + "\n")
            fp.close()

        except Exception, e:
            print("Exception occured:" + str(e))

        time.sleep(2)