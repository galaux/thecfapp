.PHONY: clean
clean:
	@echo Cleaning up …
	rm -rf ./dist

.PHONY: format
format:
	@echo Formatting …
	yarn pretty-quick

.PHONY: lint
lint:
	@echo Linting …
	./node_modules/.bin/eslint --ext ts ./src

.PHONY: lint-fix
lint-fix:
	@echo Linting …
	./node_modules/.bin/eslint --ext ts ./src --fix

# TODO
# .PHONY: lint-gql
# lint-gql: 
# 	@echo Linting graphql schema...
# 	./node_modules/.bin/graphql-schema-linter $(shell find ./src -name '*.graphql')
