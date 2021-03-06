# 微工具箱

> 使用本工具前，请确保你已经ROOT手机，并已安装busybox。你最好是个玩机专家，至少也该会刷机和修复系统。因为应用中部分高级功能可能会影响系统正常启动！！！
> 在骁龙835/821/820的设备上具有最佳体验，其它设备功能将受到限制。

---

**目前有以下功能：**
1. 关机重启：通过主界面右上角的菜单按钮打开
- [x] 快速关机 
- [x] 快速重启
- [x] 热重启
- [x] 重启到REC
- [x] 重启到Fastboot
- [x] 重启到9008模式（部分系统兼容）

2. 备份还原
- [x] 导出系统Boot分区
- [x] 刷入Boot.img到系统Boot分区
- [x] 导出系统Recovery分区
- [x] 刷入Recovery.img到系统Recovery分区
- [x] 系统打包：将Boot和System打包成可通过Recovery刷入的zip包

3.性能模式一键切换：在应用首页或通过长按应用图标（需要桌面支持）
- [x] 省电 （限制最高频率为940Mhz）
- [x] 均衡 （限制最高频率为1.4ghz）
- [x] 游戏 （限制最高频率为1.8Ghz）
- [x] 极速 （锁定CPU最低频率1.2Ghz，GPU最低450Mhz，速度快，耗电！）

4.备份还原：通过左侧抽屉菜单打开
- [x] 备份Boot：提取当前boot 为 /sdcard/boot.img
- [x] 备份Recovery：提取当前Recovery 为 /sdcard/recovery.img
- [x] 刷入Boot：选择一个img文件作为boot刷入
- [x] 刷入Recovery：选择一个img文件作为Recovery刷入
- [x] 系统打包：打包当前系统System分区、Boot分区为卡刷包，暂不支持基带打包

5.性能配置：配合辅助功能使用
- [x] 应用配置：勾选应用，点击底部的勾选按钮来指定所选应用的模式
- [x] 配置文件：在此选项卡下，挑选你喜欢的调节风格

6.后台管理：目前功能较弱，需要配合辅助功能使用
- [x] 离开销毁：被勾选的应用，在离开主界面后立即销毁相关进程（不能阻止其自动重启）
- [x] 缓存清理：每次应用切换后都执行缓存清理命令，可能会有点耗电，但内存可用会变高

7.附加功能：一些常用的脚本
- [x] 内存清理：仅限清理内存中的Cache内容，而非磁盘文件，通常用于回收缓存
- [x] ZRAM设置：需要内核支持，切换ZRAM容量可能需要很长时间来回收已用的ZRAM空间
- [x] 干掉温控模块：在Mi5设备上，干掉以后仍然有温控在运作，原因不详，但可以解决小米5无法自定义CPU频率的问题。
- [x] 删除锁屏密码：如果你在恢复系统后发现，只有指纹能用，密码却不能用了，你可以试试，前提是...你还能进来系统。
- [x] QQ净化：禁掉QQ的个性气泡和字体
- [x] MIUI精简：一键删掉MIUI系统中的多余应用（鉴于自带浏览器很丑，顺便也干了）
- [x] Flyme精简：冻结一些没什么用的自带应用
- [x] Nubia精简：冻结一些没什么用的自带应用
- [x] 自定义DPI
- [x] 自定义机型
- [x] 切换沉浸模式
- [x] 禁用Flyme下拉通知动态模糊
- [x] MIUI9去通知中心搜索框

8.Xposed功能：在xposed installer中勾选即可，无需其它设置
- [x] 王者荣耀高帧率模式：运行王者荣耀时，伪装成MI5
- [x] 自定义应用DPI
- [x] 隐藏CM等类原生系统自带Root权限管理器在状态栏显示的su图标
- [x] 强制开启Webview调试

9.应用管理
- [x] 应用卸载、冻结、解冻
- [x] 应用备份、还原

10.系统进程
- [x] 获取系统正在运行的进程，根据CPU占用率排序
- [x] 杀死选中的进程

11.SWAP设置
- [x] 通过文件开启swap
- [x] 在支持的设备上使用zram
- [x] 调整swap优先级
- [x] 开机自启动设置

12.电池充电
- [x] 充电速度修改：提高上限
- [x] 充电保护：允许自定义电量限制值
- [x] 电池状态和充电信息

13.辅助功能
- [x] 自动点击安装按钮


---

**应用界面展示**


![](https://github.com/helloklf/vtools/raw/master/Screenshot/Screenshot_1.png)

![](https://github.com/helloklf/vtools/raw/master/Screenshot/Screenshot_2.png)

![](https://github.com/helloklf/vtools/raw/master/Screenshot/Screenshot_3.png)

![](https://github.com/helloklf/vtools/raw/master/Screenshot/Screenshot_4.png)
