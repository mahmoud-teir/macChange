# Design System Specification: Architectural Precision

## 1. Overview & Creative North Star
This design system is built for high-stakes network environments where clarity is a requirement, not a feature. We are moving away from the "generic dashboard" aesthetic and toward **"Precision Functionalism."** 

This system rejects the standard rounded-corner "app look" in favor of a 0px radius architectural rigidity. It draws inspiration from premium editorial layouts and technical blueprints, using intentional asymmetry, generous white space, and massive typography scales to guide the eye. Whether in its soft neumorphic state or its high-contrast dark mode, the interface should feel like a custom-machined tool—sharp, deliberate, and expensive.

---

## 2. Colors & Surface Philosophy
The color strategy is defined by tonal depth rather than structural decoration. We use the provided Material Design tokens to create a logic of "Information Density."

### The "No-Line" Rule
Designers are strictly prohibited from using 1px solid borders to section off content. In this system, boundaries are defined by **Surface Shifts**. To separate a sidebar from a main feed, use `surface-container-low` against a `surface` background. If an element needs to stand out, it should rely on a tonal step (e.g., placing a `surface-container-highest` card on a `surface-container` background).

### Surface Hierarchy & Nesting
Treat the UI as a series of physical layers.
*   **Base:** `surface` (#131313)
*   **Secondary Logic:** `surface-container` (#1f1f1f)
*   **Interactive/Elevated:** `surface-container-highest` (#353535)

By nesting these, you create a natural "well" or "platform" effect. For example, a network log should sit within a `surface-container-lowest` well to feel embedded and secure.

### The "Glass & Gradient" Rule
To elevate the experience beyond flat HEX codes, use the `primary` (#ebffe2) to `primary_container` (#00ff41) gradient for high-value actions. For floating utility panels, apply a 40% opacity to the `surface` token with a `20px` backdrop blur. Even with 0px corners, this "frosted pane" effect provides a sophisticated sense of depth.

---

## 3. Typography
The system utilizes a high-contrast pairing: **Space Grotesk** for technical data and headers, and **Inter** for functional reading.

*   **Display (Space Grotesk):** Large-scale, tight tracking. Use `display-lg` (3.5rem) for critical metrics (e.g., Ping, Throughput). The brutalist nature of Space Grotesk commands authority.
*   **Body (Inter):** Used for documentation and configuration. The `body-md` (0.875rem) provides a neutral, highly legible counterpoint to the aggressive headers.
*   **Editorial Hierarchy:** Do not center-align. Use left-aligned, asymmetrical groupings to create a "Technical Journal" feel. Large titles should occasionally "overhang" container edges to break the grid's rigidity intentionally.

---

## 4. Elevation & Depth
In a world of 0px radii, depth is achieved through **Tonal Layering** and **Ambient Light**.

*   **The Layering Principle:** Depth is "stacked." High-priority alerts use `primary_fixed_dim` to pull the eye, while background processes recede into `surface_container_low`.
*   **Ambient Shadows:** For Neumorphic and High-Contrast modes, shadows must be diffused. Use a blur of 40px-60px at 6% opacity. The shadow color should be a tinted version of `on_surface` to simulate natural atmospheric lighting rather than a muddy grey drop shadow.
*   **The Ghost Border:** If a visual separator is required for accessibility (e.g., an input field), use the `outline_variant` (#3b4b37) at **15% opacity**. This creates a "suggestion" of a boundary without cluttering the interface with hard lines.
*   **Angular Neumorphism:** For the soft UI style, use a "Dual-Light" source: a top-left highlight of `surface_bright` and a bottom-right shadow of `surface_container_lowest`. Because the corners are 0px, this creates a "machined-metal" look rather than a bubbly plastic one.

---

## 5. Components

### Buttons
*   **Primary:** Solid `primary_container` (#00ff41) background with `on_primary` text. No border. 0px radius.
*   **Secondary:** `surface_container_high` background. On hover, transition to `surface_container_highest`.
*   **Tertiary:** Text-only using `primary` token. No background, but use a subtle `0.1rem` (Spacing 0.5) bottom-aligned bar on hover to indicate focus.

### Input Fields
Strictly rectangular. Use `surface_container_low` for the fill. Labels must use `label-sm` (Inter) and be placed **inside** the field container, top-aligned, to maintain the architectural block look. 

### Cards & Lists
*   **The "No-Divider" Mandate:** Forbid the use of horizontal lines in lists. Use **Spacing 4** (0.9rem) or **Spacing 5** (1.1rem) to create separation through white space. 
*   **Contextual Shift:** In lists of network devices, the "active" device should not have a border; it should shift its entire background to `surface_variant` or `primary_container` at 10% opacity.

### Network Visualizers (Custom Component)
Use the `outline` (#84967e) token for grid-line backgrounds in graphs. Data lines should be drawn with a 2px stroke of `primary_fixed` (#72ff70) to ensure they "glow" against the charcoal background.

---

## 6. Do's and Don'ts

### Do:
*   **Embrace the Grid:** Use the Spacing Scale (especially 8, 12, and 16) to create massive, intentional gaps between sections.
*   **Use Mono-spacing:** For IP addresses and MAC locations, use a monospaced variant of Inter to ensure character alignment.
*   **High Contrast:** In Dark Mode, ensure `primary_container` (#00ff41) is used sparingly as a "laser pointer" for the user's attention.

### Don't:
*   **No Rounded Corners:** Never deviate from the 0px scale. Even the smallest 2px radius will break the architectural integrity of the system.
*   **No Solid Borders:** Do not use `outline` at 100% opacity for boxes. It creates a "cheap" look. Use background tonal shifts instead.
*   **No Center-Alignment:** Avoid centering text in large containers. It feels like a template. Keep text left-aligned to mimic professional technical documentation.
*   **No Crowding:** If a screen feels busy, increase the spacing from `4` (0.9rem) to `8` (1.75rem). The system relies on "Breathing Room" to feel premium.