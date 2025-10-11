package langs

import "shahrohit.com/internal/types"


func CppSpec(src string) types.LangSpec {
	return types.LangSpec{
		Image: "gcc:13",
		CompileScript: `
set -e
mkdir -p /workspace /tmp
cat >/tmp/main.cpp <<'CPP'
` + src + `
CPP
g++ -O2 -std=gnu++17 /tmp/main.cpp -o /workspace/main
echo "compiled ok"
`,
		RunCmd: []string{"/workspace/main"},
	}
}
