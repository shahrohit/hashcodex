import HeaderProfile from "@/components/header-profile";
import { ThemeToggle } from "@/components/theme-toggle";
import Logo from "@/public/logo.svg";
import Image from "next/image";
import Link from "next/link";

export default function NavBar() {
  return (
    <header className="bg-background/95 backdrop-blur[backdrop-filter]:bg-background/60 sticky top-0 z-50 w-full border-b">
      <div className="container mx-auto flex min-h-16 items-center justify-between px-4 md:px-6 lg:px-8">
        <Link href="/" className="mr-4 flex items-center space-x-2">
          <Image src={Logo} alt="Hashcodex" className="p-5" />
        </Link>

        <div className="flex items-center space-x-4">
          <ThemeToggle />
          <HeaderProfile />
        </div>
      </div>
    </header>
  );
}
