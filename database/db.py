import urllib.request
import urllib
import json
import sqlite3
import os


nameDatabase = "gw2item.db"
def createDatabase():
    print("Create Db...")
    conn = sqlite3.connect(nameDatabase)
    c = conn.cursor()
    c.execute("drop table if exists gwitem")
    conn.commit()
    c.execute('''CREATE TABLE gwitem
             (rid INTEGER, name text, description text, iconUrl text, image bytea)''')

    conn.commit()
    conn.close()

def prepareForAndroid():
    print("Prepare...")
    conn = sqlite3.connect(nameDatabase)
    c = conn.cursor()
    c.execute("drop table if exists android_metadata")
    conn.commit()
    c.execute("CREATE TABLE 'android_metadata' ('locale' TEXT DEFAULT 'en_US')")
    conn.commit()
    c.execute("INSERT INTO 'android_metadata' VALUES ('en_US')")
    conn.commit()
    conn.close()

def insertItem(cursor, table):
    cursor.executemany("INSERT INTO gwitem ('rid','name','description', 'iconUrl') VALUES (?,?,?,?)", table)

def fillDatabase():
    percentage = 0
    url ="https://api.guildwars2.com/v2/items"
    response = urllib.request.urlopen(url)
    data = response.read()
    text = data.decode('utf-8')

    conn = sqlite3.connect(nameDatabase, timeout=2)
    cursor = conn.cursor()
    listIds = json.loads(text)

    print("Size " + str(len(listIds)))
    token = 100
    toSend = ""
    ids = [listIds[x:x+100] for x in range(0, len(listIds), 100)]
    index = 0
    for id in ids:
        toSend = str(id).replace("[","").replace("]","").replace(" ","")
        url ="https://api.guildwars2.com/v2/items?ids=" + toSend
        response = urllib.request.urlopen(url)
        data = response.read()
        text = data.decode('utf-8')
        jsonObject = json.loads(text)
        table = []
        description = ""
        name = ""
        id = ""
        iconUrl = ""
        for object in jsonObject:
            if 'description' in object:
                description = object['description']

            if 'name' in object:
                name = object['name']

            if 'id' in object:
                id = object['id']
            if 'icon' in object:
                iconUrl = object['icon']
            path = "images/" + name + ".png"
           # if not os.path.isfile(path):
            #    urllib.request.urlretrieve(iconUrl, path)
            table.append((id, name, description, iconUrl))
            index = index + 1
            percentage = (index*100)/len(listIds)
            print(percentage)

        insertItem(cursor,table) 
        conn.commit()
    conn.close()



createDatabase()
prepareForAndroid()
fillDatabase()
#os.rename(nameDatabase, "../app/src/main/assets/databases" + nameDatabase)
