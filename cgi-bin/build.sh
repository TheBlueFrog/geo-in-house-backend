# this runs on the Ubuntu box after a src.zip is uploaded

rm -Rf __MACOSX
rm -Rf bin
rm -Rf src

unzip src.zip
rm -Rf __MACOSX

mkdir bin
javac -sourcepath src -classpath bin:$MYLIBS src/com/ebay/mike/SendMessage.java -d bin
javac -sourcepath src -classpath bin:$MYLIBS src/com/ebay/mike/GetInstallations.java -d bin
javac -sourcepath src -classpath bin:$MYLIBS src/com/ebay/mike/PutNewInstallation.java -d bin
 


-sourcepath src:sc2
