# LPC
A chat formatting plugin for LuckPerms.
> [!WARNING]
> Please note that this is a branch project, meaning its functionality does not fully align with the main project, and it is still under development. We do not recommend using it in production environments. Thank you for your understanding.

---

## What new features does this project add?

We have added support for multi-dimensional/multi-world.

```yaml
# Per-world chat format. Overrides group-formats and chat-format for the specified world.
# YAML note: world names must be indented with exactly 2 spaces.
world-formats:
  world_nether: "&c[nether] {prefix}{name}&r: {message}"
  world_the_end: "&5[end] {prefix}{name}&r: {message}"
```

