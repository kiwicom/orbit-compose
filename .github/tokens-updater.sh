#!/usr/bin/env bash
set -euo pipefail

PR_COUNT=$(gh pr list --json id --label "design-tokens-autoupdate" --jq length -s open)

if [[ $PR_COUNT -ne 0 ]]; then
  echo "Design Tokens Update pull-request is already created."
  exit 1
fi

git config --local user.email "action@github.com"
git config --local user.name "GitHub Action"
git checkout -B tokens-autoupdate

./gradlew generator:colors
git add --all || true
git commit -m "tokens: update colors on $TODAY" || true

./gradlew generator:icons
git add --all || true
git commit -m "tokens: update icons on $TODAY" || true
