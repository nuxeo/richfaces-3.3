PROJECT_DIR=`pwd`

mvn  clean install -N -Dmaven.test.skip=true
cd $PROJECT_DIR/cdk
mvn  clean install -Dmaven.test.skip=true
cd $PROJECT_DIR/framework
mvn  clean install -Dmaven.test.skip=true
cd $PROJECT_DIR/ui
mvn  clean install -Dmaven.test.skip=true
#cd $PROJECT_DIR/docs
#mvn  clean install
cd $PROJECT_DIR/ui/assembly
mvn  clean install -Dmaven.test.skip=true
#cd $PROJECT_DIR/extensions
#mvn  clean install
#cd $PROJECT_DIR/samples
#mvn  clean install -N
#cd $PROJECT_DIR/samples/richfaces-demo
#mvn  clean install
