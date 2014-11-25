#!/bin/bash
cd build/jar
java -cp "../../lib/CadpageParser.jar:./CParser.jar" CParser
cd ..
cd ..
