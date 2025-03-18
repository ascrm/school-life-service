### 1. Fork 仓库

- **访问目标仓库**：打开你想贡献代码的 GitHub 仓库页面。
- **Fork 操作**：在页面右上角找到“Fork”按钮，并点击它。GitHub 会将该仓库复制到你的账户下。你可以选择将其 fork 到个人账户或你所属的组织（如果有）。

本地dev提交
### 2. 克隆 Fork 的仓库到本地
- 在你的 GitHub 账户中找到刚才 fork 的仓库，然后点击“Code”按钮获取仓库的 URL。
- 使用 `git clone` 命令克隆仓库到本地：

```
git clone https://github.com/your-username/repository-name.git
cd repository-name
```

### 3. 设置 Upstream 远程仓库

为了能够同步原始仓库的更新，需要添加一个指向原始仓库的远程仓库链接，通常命名为 `upstream`。

```
git remote add upstream https://github.com/original-owner/repository-name.git
```


### 5. 开发与提交更改

在这个新分支上进行代码修改，完成后使用以下命令提交更改：

```
git add .
git commit -m "描述你的更改"
```

### 6. 同步最新代码

在提交 Pull Request 之前，最好先同步最新的上游代码以避免冲突。

- 获取上游仓库的最新更改：


```
git fetch upstream
```

- 切换到主分支（如 `main` 或 `master`），并合并上游的更改：


```
git checkout main
git merge upstream/main
```

- 如果你在开发分支，也需要将这些更新合并到你的开发分支：


```
git checkout feature-or-fix-branch
git rebase main
```

### 7. 推送更改到 GitHub

将本地分支上的所有更改推送到你的 GitHub 仓库：

```
git push origin feature-or-fix-branch
```

### 8. 提交 Pull Request

- 访问你在 GitHub 上 fork 的仓库页面。
- 找到“Contribute”按钮或者直接进入“Pull requests”选项卡，点击“New pull request”。
- 选择你的分支作为比较分支，并填写 Pull Request 的详细信息，解释你所做的更改及其目的。
- 点击“Create pull request”。

### 9. 与维护者沟通

一旦你提交了 Pull Request，项目维护者可能会要求你做一些额外的修改或调整。根据反馈继续改进你的代码，并重复上述提交和推送过程直到 Pull Request 被接受。

### 10. 清理

当你完成贡献后，可以删除本地和远程的特性分支来保持工作区的整洁：

```
git branch -d feature-or-fix-branch
git push origin --delete feature-or-fix-branch
```

通过以上步骤，你就完成了一个完整的从 fork 到贡献代码的过程。这个流程不仅帮助你参与到开源项目中，还能有效提升你的 Git 技能。