酒鬼内参会员后端代码
-
## 模块说明
- nm-code-generator：代码生成器模块,只要是生成mybatis mapper和实体

- nm-common：公共模块,不允许使用spring相关配置

- nm-dao：数据访问层模块，只要是mybatis的mapper

- nm-model：实体模块，包含两部分一部分是和数据库表对应的实体，另一部分是业务对象如DO、BO、VO、query等

- nm-modules：以下可执行的模块,（单独启动），包含一下子模块
    1. nm-admin-api 后台管理-PC
    2. nm-wechat-api：消费者端-对外接口
 



##nm-code-generator 代码生成说明：
     1.直接执行CodeGenerator
     2.控制台输入待生成表名并回车，之后mapper和model对自动放置到nm-dao和nm-model模块中
 



## 分支说明
- master 正式环境分支
- dev 测试环境分支



