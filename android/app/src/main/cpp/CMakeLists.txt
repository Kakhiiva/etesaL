cmake_minimum_required(VERSION 3.22.1)
project("etesaL_native")

add_library(etesaL_native SHARED forkexec.c cpu_affinity.c)
target_link_libraries(etesaL_native log)