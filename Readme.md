# 团队协作流程

## 1. 克隆仓库到本地

```
   git clone https://github.com/ascrm/school-life-service.git
```
## 2. 在本地新建分支

![image](/assets/Readme/image.png)

选择新建分支，分支命名的格式： feat-功能

比如要开发一个用户登录功能就叫： feat-userLogin
比如开发一个分页查询帖子的功能就叫： feat-postPageQuery

## 3. 将本地新分支提交并推送到远程

*注意*：不是推送到远程的 dev 或 master 分支上，而是推送到远程与新分支同名的分支上。

用 idea 推送的话 idea 会自动帮你将新分支推送到远程的同名分支上（如果远程没有与新分支同名的分支，git 会自动创建）

## 4. 将远程新分支的提交合并到 dev 上
![image](/assets/Readme/image 2.png)

1. 先左上角切换为新分支
2. 然后点击右下角 Contribute，选择 Open Pull Request 发送合并请求
3. 修改要合并到的分支为 dev，不要选 master
   ![image](/assets/Readme/image 5.png)

   *注意*：如果右侧出现了 **Can’t automatically merge.** 需要手动解决一下冲突代码
4. 然后点击右下角的 Create pull request 创建和并请求
5. 如果存在冲突，则点击解决冲突手动解决一下冲突代码
   ![image](/assets/Readme/image 6.png)

   至于如何手动解决冲突代码，自行上网搜索相关教程

6. 解决完冲突后，会显示代码提交需要审核

   ![image](/assets/Readme/image 7.png)

   在右侧边栏的顶部，自己邀请团队成员进行审核
7. 等待审核完毕后即可合并代码
   ![image](/assets/Readme/image 8.png)

   当然审核员也可以点击 **合并拉去请求**，进行合并
   
8. 合并完成后，删除新分支
   ![image](/assets/Readme/image 9.png)

   点击右侧的删除分支，删除新分支


*其他注意事项*：
1. 新分支代表一个具体的功能，假如这个功能只完成了一部分，可以提交推送到远程，但是不要尝试合并到 dev 分支
2. 新建一个分支不要开发多个功能，比如一个新分支同时开发用户登录和帖子分页查询
3. 新分支一定是合并到 dev 分支，不要尝试合并到 master 分支，否则一定不会成功
4. 合并成功后，一定要记得删除新分支