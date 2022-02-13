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


assert "$# -ge 2" "must supply path to local repository and commit message" \
'Usage: ./git-push.sh <path-to-local-repo> "message"'

assert "-d $1/.git" "path does not point to a local git repository"

if [ -z "$2" ]
then 
    error "commit message must not be empty"
    exit 1
fi

ORIGIN=$( git config --get remote.origin.url )
echo "$( purple 'current remote origin' ): '$ORIGIN'"

if [ $ORIGIN != $'https://github.com/mzamora1/CSE-360-Team-Project.git' ]
then
    echo "$( yellow 'adding remote origin' ): https://github.com/mzamora1/CSE-360-Team-Project.git"
    git remote add origin https://github.com/mzamora1/CSE-360-Team-Project.git
fi


git pull origin main
assert "$? -eq 0" "could be from conflicting merge between main branch and local repository" \
"Solution: use 'git mergetool' or 'git diff' to find and correct all merge conflicts." \
"Solution: if you want to overwrite local conflicts with remote state use 'git pull --force origin main'" \
"Solution: if you want to overwrite remote conflicts with local state use 'git push --force origin main'" \
"Solution: Google how to resolve git merge conflicts"
success "fetched and merged main branch"

git add $1 && success "added '$1'" && \
git commit -m "$2" && success "commited '$2'" && \
git push origin master:main && success "pushed to main branch"


