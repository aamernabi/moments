Tell Git to ignore changes to gradle.properties on your local machine:

```git update-index --skip-worktree gradle.properties```

This will cause local changes not to get committed. If you ever do want to commit changes to the file,
you'll have to undo this with the **--no-skip-worktree** flag.

```git update-index --no-skip-worktree gradle.properties```