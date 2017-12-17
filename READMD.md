# Preparing the environment 

Java DB is installed automatically as part of the Java SE Development Kit (JDK).
Set following envrironmental variables

    export JAVA_HOME=/usr/lib/jvm/java-8-oracle  # Where JDK is installed
    export DERBY_HOME=$JAVA_HOME/db
	export PATH=$PATH:$DERBY_HOME/bin

Set classpath to include all the necessary jars.
You can use Derby provided script
    
    $ source $DERBY_HOME/bin/setEmbeddedCP  # Use of source command or . is necessary to set CLASSPATH on *nix


# Using Derby tools

Derby provides following tools.

    * sysinfo   -  Displays information about your Java environment and your version of Derby.
    * ij        -  JDBC tool that you can use to run interactive queries against a Derby database. # Within Java program
    * dblook    -  Dumps all or parts of the DDL of a user-specified database to either a console or a file.

## sysinfo

	$ sysinfo                                          # Using shell
	$ java [options] -jar $DERBY_HOME/lib/derbyrun.jar # Using jar
    java org.apache.derby.tools.sysinfo                # Within Java program

### Examples
    $ mkdir MyDbDirectory
    $ cd MyDbDirectory/
    $ ij
    > CONNECT 'jdbc:derby:firstdb;create=true';

    > CREATE TABLE FIRSTTABLE (ID INT PRIMARY KEY, NAME VARCHAR(12));

    > INSERT INTO FIRSTTABLE VALUES (10,'TEN'),(20,'TWENTY'),(30,'THIRTY');

    > SELECT * FROM FIRSTTABLE;

    > SELECT * FROM FIRSTTABLE WHERE ID=20;

    > run 'MY_WRITTEN_SQL_QUERY.sql';

    > disconnect;
    > exit;

    $ tail -f MyDbDirectory/derby.log  # For Derby loggin messages
## ij
    
    $ ij                                              # Using shell
    $ java -jar $DERBY_HOME/lib/derbyrun.jar ij       # Using jar 
    java org.apache.derby.tools.ij                    # Within Java program

## dblook

    $ dblook -d connectionURL [options]  # Using shell
    $ java [options] -jar $DERBY_HOME/lib/derbyrun.jar dblook -d connectionURL [options] # Using jar
    java org.apache.derby.tools.dblook -d connectionURL [options] # Within Java program
