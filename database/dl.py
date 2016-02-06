import urllib.request
import urllib
import json
import sys
from lxml import html
import requests
import bs4
import shutil


def dlImages():
    url = 'https://api.guildwars2.com/v2/files?ids=all'
    response = urllib.request.urlopen(url)
    data = response.read()
    text = data.decode('utf-8')
    print(text)
    d = json.loads(text)
    
    for row in d:
        name = row['id']
        icon = row["icon"]
        path = sys.argv[1] + name + ".png"
        print(path)
        urllib.request.urlretrieve(icon, path)

def dlQuagganImages():
    url = 'https://api.guildwars2.com/v2/quaggans?ids=all'
    response = urllib.request.urlopen(url)
    data = response.read()
    text = data.decode('utf-8')
    print(text)
    d = json.loads(text)
    
    for row in d:
        name = row['id']
        icon = row["url"]
        path = sys.argv[1] + name + ".png"
        print(icon)
        response = urllib.request.urlopen(icon) 
        out_file = open(path, 'wb')
        shutil.copyfileobj(response, out_file)

def dlDataWebsite():
    response = requests.get("https://wiki.guildwars2.com/wiki/Adamant_Guard_Dagger")
    soup = bs4.BeautifulSoup(response.text)
    table = soup.find('table', attrs={'class':'npc sortable table'})
    tds = table.find_all('td')
    for td in tds:
        if td.div:
            print(td.div.find("a",{"title":True})["title"])
        if td.span:
            print(td.span.find("a",{"title":True})["title"])
        print(td.text)

def dlSpeImages(directory):
    url = 'https://api.guildwars2.com/v2/specializations?ids=all'
    response = urllib.request.urlopen(url)
    data = response.read()
    text = data.decode('utf-8')
    specializations = json.loads(text)
    for spe in specializations:
        id = spe['id']
        icon = spe["background"]
        path = directory + str(id) + ".png"
        print(path)
        urllib.request.urlretrieve(icon, path)


#dlDataWebsite()
dlQuagganImages()
#dlSpeImages(sys.argv[1])
