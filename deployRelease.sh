settings=~/.m2t/settings.xml

case "`uname`" in
  CYGWIN*) settings=`cygpath -w $settings` ;;
esac

PROJECT_DIR=`pwd`

mvn -s $settings -P local,docs,release clean deploy -N
cd $PROJECT_DIR/cdk
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/framework
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/ui
mvn -s $settings -P local,docs,release clean deploy -N
mvn -s $settings -P local,docs,release clean install
cd $PROJECT_DIR/docs
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/ui/assembly
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/extensions
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/samples
mvn -s $settings -P local,docs,release clean deploy -N
cd $PROJECT_DIR/samples/laguna
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/samples/glassX
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/samples/darkX
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/samples/themes
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/samples/violetRays
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/samples/richfaces-demo
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/examples/photoalbum
mvn -s $settings -P local,docs,release clean deploy
cd $PROJECT_DIR/examples/photoalbum/assembly
mvn -s $settings -P local,docs,release clean install