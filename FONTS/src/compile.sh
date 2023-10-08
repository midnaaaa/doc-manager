./clean.sh

javac --release 11 MainFrame.java
jar cmf MainFrame.mf ../../EXE/MainFrame.jar domini/classes/*.class domini/controladors/*.class domini/exceptions/*.class domini/utils/*.class persistencia/classes/*.class persistencia/controladors/*.class presentacio/*.class presentacio/vistes/*.class MainFrame.class MainFrame\$\1.class


javac -cp ../lib/junit-4.13.2.jar:. --release 11 test/domini/AutorTest.java test/domini/DocumentTest.java test/domini/ExpBoolTest.java test/domini/IndexAutorsTest.java test/domini/IndexDocumentTest.java test/domini/IndexExpBoolTest.java test/domini/IndexParaulaTest.java test/domini/ParaulaTest.java test/utils/UtilsTest.java test/utils/NodeTest.java test/utils/PairTest.java

cp test/domini/*.class ../../EXE/Tests
cp test/utils/*.class ../../EXE/Tests
