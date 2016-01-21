import urllib.request
import urllib
import json
import sys

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


