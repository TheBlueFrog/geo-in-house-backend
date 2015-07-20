# this runs on the C3 virtual Ubuntu box after src.zip's are uploaded

rm -Rf bin

rm -Rf __MACOSX
rm -Rf src
rm -Rf src2

unzip src.zip
unzip src2.zip

rm -Rf __MACOSX

mkdir bin

javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/AddEventForwardingTarget.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/GetInstallation.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/GetInstallations.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/PutNewEventForwarding.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/PutNewFence.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/PutNewInstallation.java -d bin

javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/SendMessage.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/UploadData.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/LoginKijijiCA.java -d bin
javac -sourcepath src:src2 -classpath bin:$MYLIBS src/com/ebay/mike/LoginAlamaulaAR.java -d bin

