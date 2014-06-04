rm -Rf __MACOSX
rm -Rf bin
rm -Rf src

unzip src.zip
rm -Rf __MACOSX

mkdir bin
javac -d bin src/com/ebay/mike/Register.java 


