#!/bin/sh

# Format the `api-clj` sub-project edn and clj files that are currently staged.
# This script works nicely when setup as a Git pre-commit hook:
#   cat ../.git/hooks/pre-commit
#   #!/bin/sh
#   . "$(dirname "$0")/../../api-clj/bin/format-api-clj.sh"

echo "Formatting …"
git diff --cached --name-status ./api-clj/**/*{clj,edn} \
  | grep -e '^[MAR]' \
  | while IFS=$'\t' read mode fname; \
    do
      echo "• ${fname}"
      zprint -w "${fname}"
      git add "${fname}"
    done
