#!/bin/sh

# Format the `api` sub-project edn and clj files that are currently staged.
# This script works nicely when setup as a Git pre-commit hook:
#   cat ../.git/hooks/pre-commit
#   #!/bin/sh
#   . "$(dirname "$0")/../../api/bin/format-api.sh"

echo "Formatting …"
git diff --cached --name-status ./api/**/*{clj,edn} \;
  | grep -e '^[MAR]' \
  | while IFS=$'\t' read mode fname; \
    do
      echo "• ${fname}"
      zprint -w "${fname}"
      git add "${fname}"
    done
