#!/usr/bin/env bash
set -o pipefail
set -u

PR_COUNT=$(gh pr list --json id --label "tokens" --jq length -s open)

if [[ $PR_COUNT -ne 0 ]]; then
  echo "Design Tokens Update pull-request is already created."
  exit 0
fi

TODAY=$(date '+%Y-%m-%d')

git config --local user.email "action@github.com"
git config --local user.name "GitHub Action"
git checkout -B tokens-autoupdate

./gradlew generator:colors
git add --all
git commit -m "tokens: update colors on $TODAY"
COLORS_STATE=$?

./gradlew generator:icons
git add --all
git commit -m "tokens: update icons on $TODAY"
ICONS_STATE=$?

if [[ $COLORS_STATE == 0 ]] || [[ $ICONS_STATE == 0 ]]; then
  git push -fu
  gh pr create --title "Design Tokens Update on $TODAY" --body "" --reviewer "kiwicom/orbit-compose" --label "tokens" --head origin/tokens-autoupdate
fi
