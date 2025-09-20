# Git & GitHub PR Cheatsheet

## Prerequisites
- Git installed
- GitHub account
- GitHub CLI (optional but recommended)

## 1. Initial Setup (One-time)

### Install GitHub CLI (Windows)
```powershell
# Using winget (Windows Package Manager)
winget install --id GitHub.cli

# Or download from: https://cli.github.com/
```

### Configure Git (One-time)
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### Authenticate GitHub CLI (One-time)
```bash
gh auth login
# Follow the prompts to authenticate
```

---

## 2. Standard Workflow

### 1. Get Latest Changes
```bash
git checkout develop
git pull origin develop
```

### 2. Create a New Branch
```bash
git checkout -b feature/your-feature-name
```

### 3. Make Your Changes
```bash
# Make your code changes...
```

### 4. Stage Changes
```bash
# Stage specific files
git add path/to/file1 path/to/file2

# Or stage all changes
git add .
```

### 5. Commit Changes
```bash
git commit -m "Your descriptive commit message"
```

### 6. Push to Remote
```bash
git push -u origin feature/your-feature-name
```

### 7. Create Pull Request (PR)

#### Using GitHub CLI (Recommended)
```bash
gh pr create \
  --base develop \
  --head feature/your-feature-name \
  --title "Your PR Title" \
  --body "## Description\nDetailed description of changes\n\n## Changes\n- Change 1\n- Change 2"
```

#### Using Git Only
1. After pushing, GitHub will show a link to create a PR
2. Or visit: `https://github.com/username/repo/compare/develop...feature/your-feature-name`

---

## 3. Common Commands

### Check Status
```bash
git status
```

### View Changes
```bash
git diff
```

### View Commit History
```bash
git log --oneline
```

### Update Branch with Latest Changes
```bash
git fetch origin
git rebase origin/develop
```

### Force Push (After Rebasing)
```bash
git push --force-with-lease
```

---

## 4. Troubleshooting

### Undo Last Commit
```bash
git reset --soft HEAD~1
```

### Undo Local Changes
```bash
# Discard all local changes
git restore .

# Discard changes to specific file
git restore path/to/file
```

### Fix Merge Conflicts
1. After `git pull` shows conflicts:
```bash
# Open files and resolve conflicts
# Then mark as resolved
git add .
git rebase --continue
```

---

## 5. After PR is Merged
```bash
# Switch to develop
git checkout develop

# Pull latest changes
git pull origin develop

# Delete local feature branch
git branch -d feature/your-feature-name

# Delete remote branch (if needed)
git push origin --delete feature/your-feature-name
```

---

## 6. GitHub CLI Commands

### List PRs
```bash
gh pr list
```

### View PR Details
```bash
gh pr view <pr-number>
```

### Checkout PR Locally
```bash
gh pr checkout <pr-number>
```

### Merge PR
```bash
gh pr merge <pr-number> --merge --delete-branch
```

---

## Best Practices
1. Keep PRs small and focused
2. Write clear commit messages
3. Update documentation when needed
4. Run tests before pushing
5. Request reviews from teammates
6. Delete merged branches

## Common Issues
- **403 Forbidden**: Run `gh auth login`
- **No such ref**: Make sure you've pushed your branch
- **Merge conflicts**: Resolve conflicts before merging

---

*Note: Replace placeholders like `your-feature-name`, `username`, and `repo` with your actual values.*
