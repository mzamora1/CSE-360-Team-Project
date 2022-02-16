#!/usr/bin/env bash

red() {
    echo -e "\e[1;31m$1\e[0m"
}
green() {
    echo -e "\e[1;32m$1\e[0m"
}
yellow() {
    echo -e "\e[1;33m$1\e[0m"
}
purple() {
    echo -e "\e[1;35m$1\e[0m"
}

error() {
    echo "$( red "ERROR" ): $1"
}

success() {
    echo "$( green "SUCCESS" ): $1" 
}

assert() {
    # echo $1
    if ! [ $1 ]
    then
        error "$2"
        until [ -z "$3" ]  # Until all parameters used up . . .
        do
            echo "$3"
            shift
        done
        exit 1
    fi
}

CWD=$( pwd )
assert "-d $CWD/.git" "current working directory does not point to a local git repository" \
"Solution: use 'cd <path-to-local-repo>'"

NAME=$( git config --get user.name )
EMAIL=$( git config --get user.email )
if [ "${NAME}d" == "d" ]
then
    read -p "Enter your first and last name. ex. John Smith (used in commit msg): " REALNAME
    git config --global user.name $REALNAME
fi
if [ "${EMAIL}d" == "d" ]
then
    read -p "Enter your GitHub email (used in commit msg): " REALEMAIL
    git config --global user.email $REALEMAIL
fi


assert "$# -ge 2" "must supply path to pass to git add and a commit message" \
'Usage: ./git-push.sh <path-to-local-repo> "message"'

if [ -z "$2" ]
then 
    error "commit message must not be empty"
    exit 1
fi

CORRECT_ORIGIN="https://github.com/mzamora1/CSE-360-Team-Project.git"
ORIGIN=$( git config --get remote.origin.url )
echo "$( purple 'current remote origin' ): '$ORIGIN'"

if [ $ORIGIN != $CORRECT_ORIGIN ]
then
    echo "$( yellow 'adding remote origin' ): $CORRECT_ORIGIN"
    git remote add origin $CORRECT_ORIGIN
fi


git pull origin main
assert "$? -eq 0" "could be from conflicting merge between main branch and local repository" \
"Solution: use 'git mergetool' or 'git diff' to find and correct all merge conflicts." \
"Solution: if you want to overwrite local conflicts with remote state use 'git pull --force origin main'" \
"Solution: if you want to overwrite remote conflicts with local state use 'git add . && git commit -m \"message\" && git push origin master:main'" \
"Solution: Google how to resolve git merge conflicts"
success "fetched and merged main branch"

git add $1 && success "added '$1'" && \
git commit -m "$2" && success "commited '$2'" && \
git push origin master:main && success "pushed to main branch" || error "failed to push to main branch"


