#-----------------------------------------------------------------------
# Define compiler and compiler flag variables for Java
#-----------------------------------------------------------------------

# Generate all debugging info
J_DEBUGGING_FLAG = -g
# Flag for class path
J_DIRECTORY_CLASS_FLAG = -d
# Specify the path where to place .class files
J_DIRECTORY_CLASS_PATH = ./bin/
# Specify where to place generated class files
J_DIRECTORY_CLASS = $(J_DIRECTORY_CLASS_FLAG) $(J_DIRECTORY_CLASS_PATH)

# Flag for source path
J_DIRECTORY_SOURCE_FLAG = -sourcepath
# Specify the path where are the source files .java
J_DIRECTORY_SOURCE_PATH = ./src/
# Specify where are the source files .java
J_DIRECTORY_SOURCE = $(J_DIRECTORY_SOURCE_FLAG) $(J_DIRECTORY_SOURCE_PATH)

# Tous les fichiers récursivement (même ceux autres que .java)
SRC = $(shell find ./src -type f)

# Création des fichiers .class correspondant
OBJ = $(SRC:.java=.class)

JC = javac
JAR = jar
JAR_FLAG = cvfm
EXEC = Main.jar
MANIFEST_PATH = ./META-INF
LIBS = lib/json.jar
JAVAC_COMPILE = $(JC) $(J_DIRECTORY_SOURCE) $(J_DIRECTORY_CLASS) 

#-----------------------------------------------------------------------
# Default Java compilation
#-----------------------------------------------------------------------

default : run

# Creation des .class dans le dossier bin.
%.class: %.java
	@$(JAVAC_COMPILE)  -cp $(LIBS) $*.java

run: bin $(OBJ)
	java -cp bin:$(LIBS) main.Main
# Creation du dossier bin
bin: 
	@mkdir -p ./bin/

jar: bin $(OBJ)
	$(JAR) $(JAR_FLAG) -cp $(LIBS) $(EXEC) $(MANIFEST_PATH)/MANIFEST -C $(J_DIRECTORY_CLASS_PATH) .

# Clean up but keep executables
clean:
	rm -Rfv bin/*

# Détruit également le dossier bin
fullclean: clean
	rmdir ./bin

