import zipfile
import sys
import os

nameFile = sys.argv[1]
fh = open(nameFile, "rb")
z = zipfile.ZipFile(fh)

insideFolder = False

for name in z.namelist():
    n = name.split("/")
    if n[1] == "android" and len(n) == 4 and n[3] !="":
        insideFolder = True
        outpath = sys.argv[2] + n[2] + "/" + n[3]
        fo = open(outpath, "wb")
        print(outpath)
        fo.write(z.read(name))

fh.close()
