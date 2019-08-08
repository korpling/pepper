#!/bin/bash

# Used by CI to deploy the existing target/repository/<version> directory to Github Pages

if [ -n "$GITHUB_API_KEY" ]; then
    cd "$TRAVIS_BUILD_DIR"

    echo "cloning gh-pages from ${TRAVIS_REPO_SLUG}"
    git clone -q  -b gh-pages https://$GITHUB_API_KEY@github.com/${TRAVIS_REPO_SLUG} gh-pages &>/dev/null
    cd gh-pages
    mkdir -p p2/${SHORT_VERSION}
    cd p2/${SHORT_VERSION}
    # remove all old file
    rm -Rf *
    # copy the P2 repository content from the maven build directory
    cp -R ${TRAVIS_BUILD_DIR}/target/repository/* .
    git add .
    git -c user.name='travis' -c user.email='travis' commit -m "add p2 repository for version ${SHORT_VERSION}"
    echo "pushing to gh-pages to ${TRAVIS_REPO_SLUG}"
    git push -q https://$GITHUB_API_KEY@github.com/${TRAVIS_REPO_SLUG} gh-pages &>/dev/null
    cd "$TRAVIS_BUILD_DIR"
else
	>&2 echo "Cannot deploy P2 repository because GITHUB_API_KEY environment variable is not set"
	exit 1
fi