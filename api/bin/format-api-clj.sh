#!/bin/sh

# Format the `api` sub-project edn and clj files that are currently staged.
# This script works nicely when setup as a Git pre-commit hook:
#   cat ../.git/hooks/pre-commit
#   #!/bin/sh
#   . "$(dirname "$0")/../../api/bin/format-api.sh"

for file in $(git diff --cached --name-only --diff-filter=ACM | grep -E '\.(clj|cljs|edn)$')
do
  zprint --formatted-write "${file}"
  git add "${file}"
done

