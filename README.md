# glue
The G.L.U.E.

# How to run

1. Run spring boot application with active profiles: `thread-pool,virtual-thread-pool,cell-delay`
   - Press start button
   - Program runs fine
   - TODO - resources consumption?
2. Run spring boot application with active profiles: `thread-pool,physical-thread-pool,cell-delay`
   - Press start button
   - Program crashes with OOM - cannot create more than 4044 threads