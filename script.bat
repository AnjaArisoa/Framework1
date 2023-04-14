cd framework/src
javac -d ../bin utils/*.java
javac -d ../bin etu2007/annotation/*.java
javac -d ../bin etu2007/framework/*.java
javac -d ../bin etu2007/framework/servlet/*.java
cd ..
cd bin
jar -cvf ../../fw.jar *
copy D:\S4\Dossier\fw.jar C:\Apache\lib\fw.jar
copy D:\S4\Dossier\fw.jar D:\S4\Dossier\test-framework\WEB-INF\lib\fw.jar
cd ../../test-framework/src
javac -d ../WEB-INF/classes model/*.java
cd ..
jar -cvf C:\Apache\webapps\test-framework.war *