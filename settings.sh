
# Build settings
# directory to place build artifacts
OUTDIR=output
# name of package to build
PACKAGE=restaurant
# modules that $PACKAGE depends on
MODULES="javafx.controls,javafx.fxml"
# entry point to the application
MAIN_CLASS=App
# top level directory of all source and resource files
SRCDIR=src/main
# directory containing java implementation files (*.java)
JAVADIR=$SRCDIR/java
# directory with similar structure to $JAVADIR containing runtime resources 
RESOURCEDIR=$SRCDIR/resources
# directory where runtime image will be placed
extract_arch_type() {
    local OS=$( uname -s )
    local ARCH=$( uname -m )
    case "$OS" in
        Linux*)     OS=Linux;;
        Darwin*)    OS=Mac;;
        CYGWIN*)    OS=Cygwin;;
        MINGW*)     OS=MinGw;;
        *)          OS="UNKNOWN:$OS"
    esac
    echo $ARCH$OS
}
PACKAGE_OUTDIR=$OUTDIR/${PACKAGE}_$( extract_arch_type )

# Convience functions
purple() { printf "\e[1;35m$*\e[0m"; }
yellow() { printf "\e[1;33m$*\e[0m"; }
warning() { printf "$( yellow "WARNING" ): $*\n"; }
green() { printf "\e[1;32m$*\e[0m"; }
success() { printf "$( green "SUCCESS" ): $*\n"; }
red() { printf "\e[1;31m$*\e[0m"; }
error() {
    printf "$( red "ERROR" ): $*\n"
    return 1
}
ok() { 
    [ $? -eq 0 ]
    return $?
}
assertHasCommands() { 
    for COMMAND in $1; do
        command -V $COMMAND > /dev/null 2>&1
        if ! ok; then
            error "cannot find '$COMMAND'\nMake sure it is visible and added to your \$PATH by typing '$COMMAND --version'"
            until [ -z "$2" ]; do  # Until all parameters used up . . .
                echo "$2"
                shift
            done
            exit 1
        fi
    done
}
fatalError() { error "$*" ; exit 1 ; }
askYesNo() {
    printf "$1\n(yes/no): "
    read CHOICE <&1
    case $CHOICE in 
        yes | y | Y | YES | 1) 
            return 0
            ;;
        *)
            return 1
            ;;
    esac
}
askForFx() {
    until [ -e "$JAVAFX/javafx.base.jar" ]; do
        warning "\$JAVAFX does not point to the library directory for javafx\n"
        read -p "Enter the path to javafx/lib on your computer: " JAVAFX <&1
    done
}
add_setting() {
    printf "\n$1='$2'\n" >> mysettings.sh || fatalError "failed saving $1 to mysettings.sh"
    warning "added $1='$2' in mysettings.sh"
}

# Import settings in mysettings.sh
if [ -r mysettings.sh ]; then 
    source mysettings.sh
    if [ ! -e "$JAVAFX/javafx.base.jar" ]; then
        askForFx
        cat mysettings.sh | grep -L "JAVAFX" > mysettings.sh
        add_setting JAVAFX "$JAVAFX"
    fi
else 
    askForFx
    add_setting JAVAFX "$JAVAFX"
fi