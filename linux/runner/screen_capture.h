#ifndef RUNNER_SCREEN_CAPTURE_H_
#define RUNNER_SCREEN_CAPTURE_H_

#include <flutter_linux/flutter_linux.h>

// канал etesaL/linux_screen: снимок экрана для QR, X11 корнем окна, Wayland порталом Screenshot
void etesaL_screen_capture_register(FlBinaryMessenger* messenger);

#endif  // RUNNER_SCREEN_CAPTURE_H_
