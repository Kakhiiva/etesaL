#ifndef RUNNER_GLOBAL_HOTKEYS_H_
#define RUNNER_GLOBAL_HOTKEYS_H_

#include <flutter_linux/flutter_linux.h>

// канал etesaL/linux_hotkeys: X11 захватом клавиш, на Wayland портал GlobalShortcuts
void etesaL_global_hotkeys_register(FlBinaryMessenger* messenger);

#endif  // RUNNER_GLOBAL_HOTKEYS_H_
