# Branching Guide

---

### Working on your task:

1. Pull the last changes from `master` branch.
2. Create a new branch from `master` and set the name for it in the format `feature_number_description`. 
   As an example: `feature_03_add_checkstyle_support`.
3. Prepare your branch for pushing to the remote repository.
    - update your local `master` from remote
      > git pull origin master
    - switch to your feature branch and make a rebase on `master`
      > git rebase master
4. Push your feature branch to the remote repository
    > git push -u origin **\<your feature branch\>**
