# Slowchat Project

## Introduction


## Guidelines

### Git and Gitlab
#### Git branch
To create a new branch, the following rules must be respected:
- Use the 3 next branch folders: 
    - feat: feature implemetation
    - fix: bug correction
    - nfr: not feature requested
- Put at the beginning of each branch name the number of the Jira ticket (MOBILE-XXX). Example 'feat/MOBILE-166_branch_name'.
- To replace a space use "_" and not "-".

#### Merge Request (MR) or Pull request (PR)
##### Creating a MR or PR
When creating a merge request or a pull request on GitLab, the following rules must be respected:
- The link of the ticket must be in the description of the MR/PR.
- If the title of the MR/PR is not explicit, add a description of all your changes.
- If the MR/PR has to be merged after another one, it must be drafted and a message must be added in the description (MR blocked by Link)

##### Code Review
When you do a code review, you must add the tag **"correction:waiting"** at the end of the code review. At the end of the correction, you must remove the previous tag to add the tag **"correction:finished"**.

Only people who have opened a thread ("comment") can close it.
