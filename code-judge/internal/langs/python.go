package langs

import "shahrohit.com/internal/types"

func PythonSpec(src string) types.LangSpec {
	return types.LangSpec{
		Image: "python:3.11-alpine",
		// No real compile; just write the source into /workspace so the run step can execute it.
		CompileScript: `
set -e
mkdir -p /workspace
cat >/workspace/main.py <<'PY'
` + src + `
PY
echo "prepared ok"
`,
		RunCmd: []string{"python3", "-B", "-u", "/workspace/main.py"},
	}
}
