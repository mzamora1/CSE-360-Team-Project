#!/usr/bin/env bash

# This script will push specified local changes to GitHub.
# It takes two parameters:
#   1: path to the file or directory you want to 'git add'
#   2: commit message (ie. "fixed some bug")
# You must execute this script from a local git repository
# Complete Usage: 
# 
#   ./git-push <path-in-local-repo> "COMMIT MESSAGE"
# 

# First time setup can also be done using:
# 
#   curl https://raw.githubusercontent.com/mzamora1/CSE-360-Team-Project/main/git-push.sh | bash 
# 
# This will not push anything to GitHub. It will only configure git so that you can 
# then push to GitHub manually or by rerunning this script with the correct arguments.

red() { echo -e "\e[1;31m$1\e[0m"; }
green() { echo -e "\e[1;32m$1\e[0m"; }
yellow() { echo -e "\e[1;33m$1\e[0m"; }
purple() { echo -e "\e[1;35m$1\e[0m"; }
warning() { echo "$( yellow "WARNING" ): $1"; }
success() { echo "$( green "SUCCESS" ): $1"; }
error() {
    echo "$( red "ERROR" ): $1"
    return 1
}

assert() {
    # echo $1
    if ! [ $1 ]; then
        error "$2"
        until [ -z "$3" ]; do  # Until all parameters used up . . .
            echo "$3"
            shift
        done
        exit 1
    fi
}

USAGE="$( purple "Usage" ): ./git-push.sh <path-in-local-repo> 'message'"
CORRECT_ORIGIN="https://github.com/mzamora1/CSE-360-Team-Project.git"
CWD=$( pwd )

if ! [ -d "$CWD/.git" ]; then
    warning "current working directory does not point to a local git repository"
    read -p "Do you want to clone the repository into its own folder in this directory? (yes/no)" CHOICE
    case $CHOICE in
        yes | y | Y | YES | 1) 
            git clone $CORRECT_ORIGIN
            success "cloned CSE-360-Team-Project to '$CWD/CSE-360-Team-Project'"
            cd CSE-360-Team-Project
            ;;
        *)
            error "rerun in a local git repository"
            exit 1
            ;;
    esac
fi

ORIGIN=$( git config --get remote.origin.url )
NAME=$( git config --get user.name )
EMAIL=$( git config --get user.email )

if [ -z "$ORIGIN" ]; then
    echo "$( yellow 'adding remote origin' ): $CORRECT_ORIGIN"
    git remote add origin $CORRECT_ORIGIN
elif [ $ORIGIN != $CORRECT_ORIGIN ]; then
    echo "$( yellow 'setting remote origin' ): $CORRECT_ORIGIN"
    git remote set-url origin $CORRECT_ORIGIN
fi

if [ -z "$NAME" ]; then
    read -p "Enter your first and last name. ex. 'John Smith' (used in commit msg): " NAME
    git config --global user.name $NAME
fi

if [ -z "$EMAIL" ]; then
    read -p "Enter your GitHub email (used in commit msg): " EMAIL
    git config --global user.email $EMAIL
    success "use 'cd '$( pwd )'' and rerun this file to push changes to GitHub"
fi


# end of setup

assert "$# -ge 2" "must supply path to pass to git add and a commit message" "$USAGE"

if [ -z "$2" ]; then 
    error "commit message must not be empty"
    exit 1
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


