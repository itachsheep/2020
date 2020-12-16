//
//  main.cpp
//  FirstDemo
//
//  Created by wei tao on 2020/12/16.
//  Copyright © 2020 wei tao. All rights reserved.
//

#include <iostream>
#include <glad/glad.h>
#include <GLFW/glfw3.h>

int main(int argc, const char * argv[]) {
    // insert code here...
//    std::cout << "Hello, World!\n";
//    return 0;
    
    // 实例化GLFW窗口
    glfwInit();
    
    // 使用glfwWindowHint函数来配置GLFW
    
    // 因为使用的版本是version3.3 所以需要告诉GLFW 主版本是3
    // 主版本号
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    // 次版本号
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    
    // 告诉GLFW我们使用的是核心模式(Core-profile)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    
    // 使用的是Mac OS X系统，你还需要加下面这行代码到你的初始化代码中这些配置才能起作用
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    
    /* 接下来我们创建一个窗口对象，这个窗口对象存放了所有和窗口相关的数据，而且会被GLFW的其他函数频繁地用到。
     * 第一个参数：窗口宽
     * 第二个参数：窗口高
     * 第三个参数：窗口的名称（标题）
     * 暂时忽略最后两个参数
     */
    GLFWwindow *window = glfwCreateWindow(800, 600, "LearnOpenGL", NULL, NULL);
    
    if (window == NULL) {
        std::cout << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }
    
    // 创建完窗口,通知GLFW将我们窗口的上下文设置为当前线程的主上下文
    glfwMakeContextCurrent(window);
    
    // GLAD是用来管理OpenGL的函数指针的，所以在调用任何OpenGL的函数之前我们需要初始化GLAD
    // 给GLAD传入了用来加载系统相关的OpenGL函数指针地址的函数。GLFW给我们的是glfwGetProcAddress，它根据我们编译的系统定义了正确的函数
    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress)) {
        std::cout << "Failed to initialize GLAD" << std::endl;
        return -1;
    }
    
    /* 在开始渲染之前还有一件重要的事情要做，必须告诉OpenGL渲染窗口的尺寸大小，即视口(Viewport)，这样OpenGL才只能知道怎样根据窗口大小显示数据和坐标。我们可以通过调用glViewport函数来设置窗口的维度(Dimension)：
     * 前两个参数控制窗口左下角的位置。第三个和第四个参数控制渲染窗口的宽度和高度（像素）
     * OpenGL幕后使用glViewport中定义的位置和宽高进行2D坐标的转换，将OpenGL中的位置坐标转换为你的屏幕坐标。例如，OpenGL中的坐标(-0.5, 0.5)有可能（最终）被映射为屏幕中的坐标(200,450)。注意，处理过的OpenGL坐标范围只为-1到1，因此我们事实上将(-1到1)范围内的坐标映射到(0, 800)和(0, 600)。
     */
    glViewport(0, 0, 800, 600);
    
    // 当用户改变窗口的大小的时候，视口也应该被调整。我们可以对窗口注册一个回调函数(Callback Function)，它会在每次窗口大小被调整的时候被调用。（ps: 在此处声明函数，在最后有函数的定义）
    void framebuffer_size_callback(GLFWwindow *window, int width, int height);
    
    /* 注册该函数，告诉GLFW我们希望每当窗口调整大小的时候调用这个函数
     * 当窗口被第一次显示的时候framebuffer_size_callback也会被调用。对于视网膜(Retina)显示屏，width和height都会明显比原输入值更高一点
     */
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
    
    // 函数声明: 输入控制 检查用户是否按下了ESC键
    void processInput(GLFWwindow *window);
    
    // 我们可能不会希望只绘制一个图像之后我们的应用程序就立即退出并关闭窗口。我们希望程序在我们主动关闭它之前不断绘制图像并能够接受用户输入。因此，我们需要在程序中添加一个while循环，我们可以把它称之为渲染循环(Render Loop)，它能在我们让GLFW退出前一直保持运行。
    // glfwWindowShouldClose函数在我们每次循环的开始前检查一次GLFW是否被要求退出，如果是的话该函数返回true然后渲染循环便结束了，之后为我们就可以关闭应用程序了。
    while (!glfwWindowShouldClose(window)) {
        
        // 在GLFW中实现一些输入控制，这可以通过使用GLFW的几个输入函数来完成。我们将会使用GLFW的glfwGetKey函数，它需要一个窗口以及一个按键作为输入。这个函数将会返回这个按键是否正在被按下。
        processInput(window);
        
        // 我们要把所有的渲染(Rendering)操作放到渲染循环中，因为我们想让这些渲染指令在每次渲染循环迭代的时候都能被执行。
        // 在每个新的渲染迭代开始的时候我们总是希望清屏，否则我们仍能看见上一次迭代的渲染结果（这可能是你想要的效果，但通常这不是）。我们可以通过调用glClear函数来清空屏幕的颜色缓冲，它接受一个缓冲位(Buffer Bit)来指定要清空的缓冲，可能的缓冲位有GL_COLOR_BUFFER_BIT，GL_DEPTH_BUFFER_BIT和GL_STENCIL_BUFFER_BIT。由于现在我们只关心颜色值，所以我们只清空颜色缓冲。
        
        /* 调用glClearColor来设置清空屏幕所用的颜色
         * 参数依次为：red green blue alpha 值均为[0, 1]
         */
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        
        // 当调用glClear函数，清除颜色缓冲之后，整个颜色缓冲都会被填充为glClearColor里所设置的颜色
        glClear(GL_COLOR_BUFFER_BIT);
        
        // glfwSwapBuffers函数会交换颜色缓冲（它是一个储存着GLFW窗口每一个像素颜色值的大缓冲），它在这一迭代中被用来绘制，并且将会作为输出显示在屏幕上
        glfwSwapBuffers(window);
        
    //glfwPollEvents函数检查有没有触发什么事件（比如键盘输入、鼠标移动等）、更新窗口状态，并调用对应的回调函数（可以通过回调方法手动设置）
        glfwPollEvents();
    }
    
    // 当渲染循环结束后我们需要正确释放/删除之前的分配的所有资源
    // 在main函数的最后调用glfwTerminate函数来完成
    glfwTerminate();
    return 0;
    
}

// 每次窗口大小被调整的时候被调用的回调函数
void framebuffer_size_callback(GLFWwindow *window, int width, int height) {
    
    glViewport(0, 0, width, height);
}

// 输入控制 检查用户是否按下了ESC键
void processInput(GLFWwindow *window) {
    // glfwGetKey 检查用户是否按下了某个键
    // 按下则返回 GLFW_PRESS
    // 未按下则返回 GLFW_RELEASE
    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
        // 按下ESC则将glfw的WindowShouldClose属性置为true，则在下个Render Loop的条件检测将会失败，程序将会关闭
        glfwSetWindowShouldClose(window, true);
    }
}
