import urllib.request
import urllib
import json
import sqlite3
import os
from shutil import copyfile

nameDatabase = "gw2item.db"
def createDatabase():
    print("Create Db...")
    conn = sqlite3.connect(nameDatabase)
    c = conn.cursor()
    c.execute("drop table if exists gwitem")
    conn.commit()
    c.execute('''CREATE TABLE gwitem
             (rid INTEGER, 
                name text, 
              description text,
                type text,
                rarity text,
                level INTEGER,
                vendor_value INTEGER,
                infoWeb text,
              iconUrl text, image bytea)''')

    conn.commit()
    conn.close()

def createDatabaseDyes():
    print("Create Db dye...")
    conn = sqlite3.connect(nameDatabase)
    c = conn.cursor()
    c.execute("drop table if exists gwdye")
    conn.commit()
    c.execute('''CREATE TABLE gwdye
             (rid INTEGER, 
                name text,
                rgbCloth text,
                rgbLeather text,
                rgbMetal text)''')

    conn.commit()
    conn.close()

def insertDye(cursor, table):
    cursor.executemany("insert into gwdye ('rid','name','rgbCloth','rgbLeather', 'rgbMetal') values (?,?,?,?,?)",table)

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
    cursor.executemany("INSERT INTO gwitem ('rid','name','description', 'type','rarity','level','vendor_value','infoWeb','iconUrl') VALUES (?,?,?,?,?, ?, ?, ?, ?)", table)

def fillDatabase():
    percentage = 0
    url ="https://api.guildwars2.com/v2/items"
    response = urllib.request.urlopen(url)
    data = response.read()
    text = data.decode('utf-8')

    conn = sqlite3.connect(nameDatabase, timeout=100)
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
        level = 0
        rarity = ""
        type = "",
        vendor_value = 0
        infoWeb = ""
        for object in jsonObject:
            if 'description' in object:
                description = object['description']
            if 'name' in object:
                name = object['name']
            if 'id' in object:
                id = object['id']
            if 'icon' in object:
                iconUrl = object['icon']
            if 'type' in object:
                type = object['type']

            if 'rarity' in object:
                rarity = object['rarity']
            if 'level' in object:
                level = object['level']

            if 'vendor_value' in object:
                vendor_value = object['vendor_value']

            path = "images/" + name + ".png"
           # if not os.path.isfile(path):
            #    urllib.request.urlretrieve(iconUrl, path)
            table.append((id, name, description, type, rarity, level, vendor_value, infoWeb, iconUrl))
            index = index + 1
            percentage = (index*100)/len(listIds)
        print(str(percentage)+"\r")

        insertItem(cursor,table) 
        conn.commit()
    conn.close()

def fillDBDye():
    url = "https://api.guildwars2.com/v1/colors.json"
    response = urllib.request.urlopen(url)
    data = response.read()
    text = data.decode('utf-8')
    jsonObject = json.loads(text)
    colors = jsonObject["colors"]
    conn = sqlite3.connect(nameDatabase, timeout=100)
    cursor = conn.cursor()
    print(len(colors))
    table = []
    for i in range(1,len(colors)):
        if str(i) in colors:
            color = colors[str(i)]
            table.append((i, color["name"], str(color["cloth"]["rgb"]),
                    str(color["leather"]["rgb"]),
                    str(color["metal"]["rgb"])))
    insertDye(cursor, table)

    conn.commit()
    conn.close()


#createDatabase()
#prepareForAndroid()
#fillDatabase()
#copyfile(nameDatabase, "../app/src/main/assets/databases/" + nameDatabase)
createDatabaseDyes()
fillDBDye()
