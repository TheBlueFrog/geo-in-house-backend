# this runs on the Ubuntu box after a src.zip is uploaded

rm -Rf __MACOSX
rm -Rf bin
rm -Rf src

unzip src.zip
rm -Rf __MACOSX

mkdir bin
javac -d bin src/com/ebay/mike/SendMessage.java 


