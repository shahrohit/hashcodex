"use client";

import { Button } from "@/components/ui/button";
import {
  FormControl,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Eye, EyeOff, Lock } from "lucide-react";
import { useState } from "react";

function PasswordField({
  field,
  label = "Password",
}: {
  field: object;
  label?: string;
}) {
  const [showPassword, setShowPassword] = useState(false);

  return (
    <FormItem>
      <FormLabel>{label}</FormLabel>
      <FormControl>
        <div className="relative w-full">
          <Lock
            className="absolute left-3 top-1/2 -translate-y-1/2 text-muted-foreground"
            size={16}
          />
          <Input
            id="password"
            type={showPassword ? "text" : "password"}
            className="pl-8 pr-8"
            placeholder="password"
            {...field}
          />
          <Button
            type="button"
            onClick={() => setShowPassword(!showPassword)}
            className="absolute right-0 top-1/2 -translate-y-1/2 text-muted-foreground"
            variant="ghost"
            size="icon"
          >
            {showPassword ? (
              <Eye size={16} />
            ) : (
              <EyeOff className="" size={16} />
            )}
          </Button>
        </div>
      </FormControl>
      <FormMessage />
    </FormItem>
  );
}

export default PasswordField;
