# Testing and Pull Request Guide: AccountsService

## Overview
This document outlines the process of creating test cases for the `AccountsService` class, pushing changes to Git, and creating a pull request (PR) for code review.

## Table of Contents
1. [Test Case Implementation](#test-case-implementation)
2. [Running Tests](#running-tests)
3. [Git Workflow](#git-workflow)
4. [Creating a Pull Request](#creating-a-pull-request)
5. [Troubleshooting](#troubleshooting)

## Test Case Implementation

### Test Cases Created
1. **createAccount_Success**
   - Tests successful account creation
   - Verifies customer and account repository interactions

2. **createAccount_WhenCustomerAlreadyExists_ShouldThrowException**
   - Tests duplicate customer handling
   - Verifies `CustomerAlreadyExistsException` is thrown

3. **createAccount_ShouldSetAccountNumberInRange**
   - Validates account number generation
   - Ensures account numbers are within the expected range (1,000,000,000 to 1,999,999,999)

### Test Dependencies
- JUnit 5
- Mockito for mocking dependencies
- Lombok for reducing boilerplate code

## Running Tests

### Prerequisites
- Java 17 or higher
- Maven

### Command to Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AccountsServiceTest
```

## Git Workflow

1. **Create a Feature Branch**
   ```bash
   git checkout -b feature/test1
   ```

2. **Stage Changes**
   ```bash
   git add src/test/java/com/eazybytes/acounts/service/AccountsServiceTest.java
   ```

3. **Commit Changes**
   ```bash
   git commit -m "Add comprehensive test cases for AccountsService"
   ```

4. **Push to Remote**
   ```bash
   git push origin feature/test1
   ```

## Creating a Pull Request

### Using GitHub CLI
1. Ensure GitHub CLI is installed and authenticated:
   ```bash
   gh auth login
   ```

2. Create PR:
   ```bash
   gh pr create \
     --base develop \
     --head feature/test1 \
     --title "Add test cases for AccountsService" \
     --body "## Description\nAdded comprehensive test cases for the AccountsService class"
   ```

### Using GitHub Web Interface
1. Go to: `https://github.com/your-username/your-repo/compare/develop...feature/test1`
2. Click "Create pull request"
3. Add title and description
4. Click "Create pull request"

## Troubleshooting

### GitHub CLI Not Found
If you see `gh: command not found`:
1. Verify GitHub CLI is installed
2. Restart your terminal
3. Try using the full path:
   ```bash
   "C:\\Program Files\\GitHub CLI\\gh.exe" --version
   ```

### Authentication Issues
If you get authentication errors:
```bash
gh auth login
```
Follow the prompts to authenticate.

### Merge Conflicts
If there are merge conflicts:
```bash
git fetch origin
git rebase origin/develop
# Resolve conflicts
git add .
git rebase --continue
git push --force-with-lease
```

## Best Practices
- Write tests before or alongside implementation (TDD)
- Keep tests focused and independent
- Use descriptive test method names
- Mock external dependencies
- Run tests before pushing changes
- Keep pull requests small and focused
- Include meaningful commit messages
- Request code reviews from teammates

## Additional Resources
- [JUnit 5 Documentation](https://junit.org/junit5/)
- [Mockito Documentation](https://site.mockito.org/)
- [GitHub CLI Documentation](https://cli.github.com/)
