#!/usr/bin/env bash
set -o pipefail
set -u

PR_COUNT=$(gh pr list --json id --label "design-tokens-autoupdate" --jq length -s open)

if [[ $PR_COUNT -ne 0 ]]; then
  echo "Design Tokens Update pull-request is already created."
  exit 0
fi

TODAY=$(date '+%Y-%m-%d')
BRANCH="tokens-autoupdate-$TODAY"

git config --local user.email "action@github.com"
git config --local user.name "GitHub Action"
git checkout -B $BRANCH

./gradlew generator:colors
git add --all
git commit -m "tokens: update colors on $TODAY"
COLORS_STATE=$?

./gradlew generator:icons
git add --all
git commit -m "tokens: update icons on $TODAY"
ICONS_STATE=$?

if [[ $COLORS_STATE == 0 ]] || [[ $ICONS_STATE == 0 ]]; then
  git push -f origin "$BRANCH"
  gh pr create --title "Design Tokens Update on $TODAY" --body "" --label "design-tokens-autoupdate" --label "feature" --base "main" --head "$BRANCH"
fi
