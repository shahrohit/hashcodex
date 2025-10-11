package langs

import "shahrohit.com/internal/types"

func JavaSpec(src string) types.LangSpec {
	return types.LangSpec{
		Image: "openjdk:21-jdk",
		CompileScript: `
set -e
mkdir -p /workspace /tmp
cat >/tmp/Main.java <<'JAVA'
` + src + `
JAVA
javac -d /workspace /tmp/Main.java
echo "compiled ok"
`,
		RunCmd: []string{"java", "-Xmx192m", "-cp", "/workspace", "Main"},
	}
}
