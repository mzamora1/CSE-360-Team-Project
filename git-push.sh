#!/usr/bin/env bash

if [ $# -lt 2 ]
then
    echo -e "ERROR: must supply path to local repository and commit message"
    echo -e 'Usage: ./git-push.sh . "message"'
    exit 1
fi

if [ ! -d $1/.git ]
then
    echo -e "ERROR: path does not point to a local repository"
    exit 1
fi

if [ -z "$2" ]
then
    echo -e "ERROR: commit message must not be empty"
    exit 1
fi

ORIGIN=$( git config --get remote.origin.url )
echo "got remote origin: '$ORIGIN'"

if [ $ORIGIN != $'https://github.com/mzamora1/CSE-360-Team-Project.git' ]
then
    echo "adding remote origin: https://github.com/mzamora1/CSE-360-Team-Project.git"
    git remote add origin https://github.com/mzamora1/CSE-360-Team-Project.git
fi

git add $1 && echo "added: '$1'" && \
git commit -m "$2" && echo "commited: $2" && \
git push origin master:main

