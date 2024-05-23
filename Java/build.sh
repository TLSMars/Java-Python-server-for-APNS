javac \
    -source 1.8 \
    -target 1.8 \
    -cp "lib/*" \
    -d target \
    src/com/*.java

java \
    -cp "target:lib/*" \
    com.Main
