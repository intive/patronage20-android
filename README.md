# patronage20-android

## Branch Naming
```
Template:
  <type>/<JIRA-NUMBER>_<subject>
  
type can be one of:
  feature: Product feature you are working on.
  fix: QA bug.
  docs: General documentation.
  refactor: General refactor of code.
  test: Unit test or other automation test.
  chore: Anything release to project setup / ci or other.
  
Examples:
  feature/PATRON2020-34_add_support_for_light_endpoint
```

## Commit Messages
```
Template:
  <type>: <subject> #<JIRA-NUMBER>

type can be one of:
  feature: Product feature you are working on.
  fix: QA bug.
  docs: General documentation.
  refactor: General refactor of code.
  test: Unit test or other automation test.
  chore: Anything release to project setup / ci or other.
  
Examples:
  docs: update readme for new commit syntax
  feature: add support for light endpoint #PATRON2020-34
```

## Creating build for testing
```
In order to create build for testing you have to:
1. Checkout proper branch
2. Create tag
  Template: 
    git tag v0.<sprint_number>-<JIRA-NUMBER>
  Example:
    git tag v0.2-PATRON2020-34
3. Push tag to origin
  git push origin <tag>


You can also check all already created tags using this command:
  git tag
```
